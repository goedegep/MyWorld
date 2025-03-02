This directory contains a directory structure to test the 'Files Controlled' functionality.

The file TestDiscStructureSpecification.xmi contains the disc structure specification. This file has the following content:

> Disc structuur specificatie:
>   Naam: Test disc structure specification
>   Omschrijving: Specification for a directory structure under 'src/test/resources' for testing this program
>   Mappen specificaties:
>     Map specificatie:
>       Map: K:\EclipseWorkspace\goedegep.pctools\target\test-classes\Test Directory Structure\directory not to be checked\controlled directory
>       Omschrijving: Controlled directory
>       Synchronisatie specificatie: Yes this is marked as synchronized
>       Versie beheer specificatie: (null)
>       Te controleren?: false
>     Map specificatie:
>       Map: K:\EclipseWorkspace\goedegep.pctools\target\test-classes\Test Directory Structure\directory to be checked
>       Omschrijving: Directory to be checked
>       Synchronisatie specificatie: (null)
>       Versie beheer specificatie: (null)
>       Te controleren?: true
>     Te negeren bestanden:
>       Bestand:
>         Naam: blablafile.bla
>         Omschrijving: Dit is alleen maar blabla
>       Te negeren mappen:
>         Map:
>           Naam: ignoreMeDirectory
>           Omschrijving: Een map die genegeerd moet worden


Description of the directory structure:
<dir> "directory not to be checked"
    Directory which doesn't have to be checked. It's only here to contain the 'controlled directory'.
    It shows that a 'controlled directory' can be part of any 'uncontrolled directory'.
    <dir> "controlled directory"
        The 'controlled directory' to be tested.
        <dir> "subdir1"
            <dir> "ignoreMeDirectory"
                A directory to be ignored completely (listed under 'Te negeren mappen').
                <file> "FileThatsNeverHandled.txt"
                    So this file shall not be found.
            <dir> "save"
                This directory contains a copy of "A controlled file.txt" in the directory "subdir 1".
                <file> "A controlled file.txt"
                    A copy of "A controlled file.txt" in the directory "subdir 1".
            <file> "A controlled file.txt"
                A file which has a copy in the directory "save".
        <file> "blablafile.bla"
            A file to be ignored completely (listed under 'Te negeren bestanden').
        <file> "fileInControlledRoot.txt"
            A file at the top level of the controlled directory.
<dir> "directory to be checked"
    This is the directory to be checked.
    <dir> "some subdir"
        <dir> "ignoreMeDirectory"
            A directory to be ignored completely (listed under 'Te negeren mappen').
            <file> "FileThatsNeverHandled.txt"
                So this file shall not be found (reported).
        <file> "A controlled file - kopie.txt"
            A copy, but with a different name", of the file "A controlled file" in the directory "subdir1".
        <file> "A controlled file.txt"
            A copy of the file "A controlled file" in the directory "subdir1".
        <file> "A file which is not in the controlled directory.txt"
            A file which has no controlled counterpart.
        <file> "blablafile.bla"
            A file to be ignored completely (listed under 'Te negeren bestanden').
    <file> "fileInControlledRoot.txt"
        This is a modified version of the file with the same name in the directory "controlled directory".
        

         
    

