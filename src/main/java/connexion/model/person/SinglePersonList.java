package connexion.model.person;

import connexion.model.person.exceptions.DuplicatePersonException;
import connexion.model.person.exceptions.PersonNotFoundException;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.List;

import static connexion.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class SinglePersonList {

    private final ReadOnlyObjectWrapper<Person> person = new ReadOnlyObjectWrapper<>();
    private final ObservableList<Person> internalSinglePersonList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableSinglePersonList =
            FXCollections.unmodifiableObservableList(internalSinglePersonList);

    public ObservableList<Person> getInternalList() {
        return internalSinglePersonList;
    }

    public ReadOnlyObjectProperty<Person> personProperty() {
        return person.getReadOnlyProperty();
    }

    public Person getPerson() {
        return personProperty().get();
    }

    public void setPerson(Person person) {
        this.person.set(person);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableSinglePersonList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SinglePersonList)) {
            return false;
        }

        SinglePersonList otherSinglePersonList = (SinglePersonList) other;
        return internalSinglePersonList.equals(otherSinglePersonList.internalSinglePersonList);
    }

    @Override
    public int hashCode() {
        return internalSinglePersonList.hashCode();
    }

    @Override
    public String toString() {
        return internalSinglePersonList.toString();
    }
}
