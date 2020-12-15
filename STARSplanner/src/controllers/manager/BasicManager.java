package controllers.manager;
import entities.EntityObject;

import java.io.IOException;
import java.util.*;
import entities.Course;
import entities.Student;
/**
 * The interface that contains basic abstract functions which will be implemented by all the concrete manager classes
 * @author chua_
 *
 * @param <E>
 * This parameter takes in objects of classes that implemented its wrapper class and wraps them into a wrapper class object.
 */
public interface BasicManager <E extends EntityObject> {
	public abstract E find(E entity_object);
	public abstract boolean create(E entity_object);
	public abstract void delete(E entity_object);
	public abstract void fileRead();
	public abstract void fileWrite();
	public abstract void printAll ();
}
