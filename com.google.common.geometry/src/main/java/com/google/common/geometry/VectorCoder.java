/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.common.geometry;

import com.google.common.geometry.PrimitiveArrays.Bytes;
import com.google.common.geometry.PrimitiveArrays.Cursor;
import com.google.common.geometry.PrimitiveArrays.Longs;
import com.google.common.primitives.ImmutableLongArray;
import com.google.common.primitives.Ints;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.AbstractList;
import java.util.List;

/**
 * An encoder/decoder of {@link List}s. Decoding is on-demand, so {@link S2Coder#isLazy() is true.
 */
@SuppressWarnings("serial")
public class VectorCoder<T> implements S2Coder<List<T>> {

  /** An encoder/decoder of {@code List<byte[]>}. */
  static final VectorCoder<byte[]> BYTE_ARRAY = new VectorCoder<>(new S2Coder<byte[]>() {
    @Override
    public void encode(byte[] value, OutputStream output) throws IOException {
      output.write(value);
    }

    @Override
    public byte[] decode(Bytes data, Cursor cursor) {
      byte[] b = new byte[Ints.checkedCast(cursor.remaining())];
      for (int i = 0; i < b.length; i++) {
        b[i] = data.get(cursor.position++);
      }
      return b;
    }

    @Override
    public boolean isLazy() {
      return false;
    }
  });

  /** An encoder/decoder of {@code List<String>}. */
  public static final VectorCoder<String> STRING = new VectorCoder<>(new S2Coder<String>() {
    /** The charset to use. Note the StandardCharsets constant is not available in Android. */
    private Charset charset = Charset.forName("UTF-8");

    @Override public void encode(String value, OutputStream output) throws IOException {
      output.write(value.getBytes(charset));
    }

    @Override public String decode(PrimitiveArrays.Bytes data, Cursor cursor) {
      byte[] b = new byte[Ints.checkedCast(cursor.remaining())];
      for (int i = 0; i < b.length; i++) {
        b[i] = data.get(cursor.position++);
      }
      return new String(b, charset);
    }

    @Override public boolean isLazy() {
      return false;
    }
  });

  /**
   * An encoder/decoder of {@link S2Shape}s, where the shapes use the {@link
   * S2TaggedShapeCoder#FAST} encoding. Decoding is on-demand.
   */
  public static final VectorCoder<S2Shape> FAST_SHAPE = new VectorCoder<>(S2TaggedShapeCoder.FAST);

  /**
   * An encoder/decoder of {@link S2Shape}s, where the shapes use the {@link
   * S2TaggedShapeCoder#COMPACT} encoding.  Decoding is on-demand.
   */
  public static final VectorCoder<S2Shape> COMPACT_SHAPE =
      new VectorCoder<>(S2TaggedShapeCoder.COMPACT);

  private final S2Coder<T> coder;

  /**
   * Constructs a {@code VectorCoder} which encodes/decodes elements with the given {@code coder}.
   */
  public VectorCoder(S2Coder<T> coder) {
    this.coder = coder;
  }

  @Override
  public void encode(List<T> values, OutputStream output) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ImmutableLongArray.Builder offsetsBuilder = ImmutableLongArray.builder(values.size());

    for (T value : values) {
      coder.encode(value, bos);
      offsetsBuilder.add(bos.size());
    }
    UintVectorCoder.UINT64.encode(Longs.fromImmutableLongArray(offsetsBuilder.build()), output);
    bos.writeTo(output);
  }

  @Override
  public EncodedList<T> decode(PrimitiveArrays.Bytes data, PrimitiveArrays.Cursor cursor) {
    Longs offsets;
    // TODO(user): Throw IOExceptions here and below.
    try {
      offsets = UintVectorCoder.UINT64.decode(data, cursor);
    } catch (IOException e) {
      throw new IllegalStateException("Underlying IO error", e);
    }
    long offset = cursor.position;
    cursor.position += (offsets.length() > 0 ? offsets.get(offsets.length() - 1) : 0);

    return new EncodedList<T>() {
      @Override
      public T get(int position) {
        long start = (position == 0) ? 0 : offsets.get(position - 1);
        long end = offsets.get(position);
        try {
          return coder.decode(data, data.cursor(offset + start, offset + end));
        } catch (IOException e) {
          throw new IllegalStateException("Underlying IO error", e);
        }
      }

      @Override
      public int size() {
        return offsets.length();
      }

      @Override
      public long encodedSize(int index) {
        long start = (index == 0) ? 0 : offsets.get(index - 1);
        long end = offsets.get(index);
        return end - start;
      }
    };
  }

  /** An encoded list. */
  public abstract static class EncodedList<T> extends AbstractList<T> {
    /** The encoded size of the given element in bytes. */
    public abstract long encodedSize(int index);
  }

  @Override
  public boolean isLazy() {
    return true;
  }
}
