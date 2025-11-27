/**
 */
package goedegep.vacations.model.util;

import goedegep.types.model.Event;
import goedegep.vacations.model.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see goedegep.vacations.model.VacationsPackage
 * @generated
 */
public class VacationsAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static VacationsPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VacationsAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = VacationsPackage.eINSTANCE;
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
  protected VacationsSwitch<Adapter> modelSwitch = new VacationsSwitch<Adapter>() {
    @Override
    public Adapter caseVacations(Vacations object) {
      return createVacationsAdapter();
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
   * Creates a new adapter for an object of class '{@link goedegep.vacations.model.Vacations <em>Vacations</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.model.Vacations
   * @generated
   */
  public Adapter createVacationsAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.model.Vacation <em>Vacation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.model.Vacation
   * @generated
   */
  public Adapter createVacationAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.model.Location <em>Location</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.model.Location
   * @generated
   */
  public Adapter createLocationAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.model.VacationElement <em>Vacation Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.model.VacationElement
   * @generated
   */
  public Adapter createVacationElementAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.model.Text <em>Text</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.model.Text
   * @generated
   */
  public Adapter createTextAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.model.Day <em>Day</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.model.Day
   * @generated
   */
  public Adapter createDayAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.model.Picture <em>Picture</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.model.Picture
   * @generated
   */
  public Adapter createPictureAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.model.GPXTrack <em>GPX Track</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.model.GPXTrack
   * @generated
   */
  public Adapter createGPXTrackAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.model.BoundingBox <em>Bounding Box</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.model.BoundingBox
   * @generated
   */
  public Adapter createBoundingBoxAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.model.Boundary <em>Boundary</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.model.Boundary
   * @generated
   */
  public Adapter createBoundaryAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.model.MapImage <em>Map Image</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.model.MapImage
   * @generated
   */
  public Adapter createMapImageAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.model.DayTrip <em>Day Trip</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.model.DayTrip
   * @generated
   */
  public Adapter createDayTripAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.model.Document <em>Document</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.model.Document
   * @generated
   */
  public Adapter createDocumentAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.model.TravelCategory <em>Travel Category</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.model.TravelCategory
   * @generated
   */
  public Adapter createTravelCategoryAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link goedegep.vacations.model.Travel <em>Travel</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see goedegep.vacations.model.Travel
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

} //VacationsAdapterFactory
