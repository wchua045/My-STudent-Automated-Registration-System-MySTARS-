package controllers.manager;
import entities.EntityObject;
/**
 * This is a child interface that inherits the abstract functions in BasicManager. 
 * The additional functions in this class are used in the adding and dropping of courses activities performed by the controller classes
 * @author chua_
 *
 * @param <E>
 */
public interface RegistrationManager <E extends EntityObject> extends BasicManager {
	public abstract boolean register(E entity_object, String matric);
	public abstract boolean deregister(E entity_object, String matric);
}
