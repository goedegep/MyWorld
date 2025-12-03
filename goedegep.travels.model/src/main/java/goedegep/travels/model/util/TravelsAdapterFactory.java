/**
 */
package goedegep.travels.model.util;

import goedegep.travels.model.*;

import goedegep.types.model.Event;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.travels.model.TravelsPackage
 * @generated
 */
public class TravelsAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static TravelsPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TravelsAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = TravelsPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object) {
    if (object == modelPackage) {
      return true;
    }
    if (object instanceof EObject) {
      return ((EObject) object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TravelsSwitch<Adapter> modelSwitch = new TravelsSwitch<Adapter>() {
    @Override
    public Adapter caseTravels(Travels object) {
      return createTravelsAdapter();
    }

    @Override
    public Adapter caseVacation(Vacation object) {
      return createVacationAdapter();
    }

    @Override
    public Adapter caseLocation(Location object) {
      return createLocationAdapter();
    }

    @Override
    public Adapter caseVacationElement(VacationElement object) {
      return createVacationElementAdapter();
    }

    @Override
    public Adapter caseText(Text object) {
      return createTextAdapter();
    }

    @Override
    public Adapter caseDay(Day object) {
      return createDayAdapter();
    }

    @Override
    public Adapter casePicture(Picture object) {
      return createPictureAdapter();
    }

    @Override
    public Adapter caseGPXTrack(GPXTrack object) {
      return createGPXTrackAdapter();
    }

    @Override
    public Adapter caseBoundingBox(BoundingBox object) {
      return createBoundingBoxAdapter();
    }

    @Override
    public Adapter caseBoundary(Boundary object) {
      return createBoundaryAdapter();
    }

    @Override
    public Adapter caseMapImage(MapImage object) {
      return createMapImageAdapter();
    }

    @Override
    public Adapter caseDayTrip(DayTrip object) {
      return createDayTripAdapter();
    }

    @Override
    public Adapter caseDocument(Document object) {
      return createDocumentAdapter();
    }

    @Override
    public Adapter caseTravelCategory(TravelCategory object) {
      return createTravelCategoryAdapter();
    }

    @Override
    public Adapter caseTravel(Travel object) {
      return createTravelAdapter();
    }

    @Override
    public Adapter caseEvent(Event object) {
      return createEventAdapter();
    }

    @Override
    public Adapter defaultCase(EObject object) {
      return createEObjectAdapter();
    }
  };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target) {
    return modelSwitch.doSwitch((EObject) target);
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.travels.model.Travels <em>Travels</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.travels.model.Travels
   * @generated
   */
  public Adapter createTravelsAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.travels.model.Vacation <em>Vacation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.travels.model.Vacation
   * @generated
   */
  public Adapter createVacationAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.travels.model.Location <em>Location</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.travels.model.Location
   * @generated
   */
  public Adapter createLocationAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.travels.model.VacationElement <em>Vacation Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.travels.model.VacationElement
   * @generated
   */
  public Adapter createVacationElementAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.travels.model.Text <em>Text</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.travels.model.Text
   * @generated
   */
  public Adapter createTextAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.travels.model.Day <em>Day</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.travels.model.Day
   * @generated
   */
  public Adapter createDayAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.travels.model.Picture <em>Picture</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.travels.model.Picture
   * @generated
   */
  public Adapter createPictureAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.travels.model.GPXTrack <em>GPX Track</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.travels.model.GPXTrack
   * @generated
   */
  public Adapter createGPXTrackAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.travels.model.BoundingBox <em>Bounding Box</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.travels.model.BoundingBox
   * @generated
   */
  public Adapter createBoundingBoxAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.travels.model.Boundary <em>Boundary</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.travels.model.Boundary
   * @generated
   */
  public Adapter createBoundaryAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.travels.model.MapImage <em>Map Image</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.travels.model.MapImage
   * @generated
   */
  public Adapter createMapImageAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.travels.model.DayTrip <em>Day Trip</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.travels.model.DayTrip
   * @generated
   */
  public Adapter createDayTripAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.travels.model.Document <em>Document</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.travels.model.Document
   * @generated
   */
  public Adapter createDocumentAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.travels.model.TravelCategory <em>Travel Category</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.travels.model.TravelCategory
   * @generated
   */
  public Adapter createTravelCategoryAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.travels.model.Travel <em>Travel</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.travels.model.Travel
   * @generated
   */
  public Adapter createTravelAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.types.model.Event <em>Event</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.types.model.Event
   * @generated
   */
  public Adapter createEventAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter() {
    return null;
  }

} //TravelsAdapterFactory
