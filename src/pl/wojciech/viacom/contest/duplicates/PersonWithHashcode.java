package pl.wojciech.viacom.contest.duplicates;

/**
 * Created by wojciechm on 2015-07-08.
 */
class PersonWithHashcode {
    String name;
    int age;

    @Override
    public int hashCode() {
        return name.hashCode() + age;
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof PersonWithHashcode)) {
            return false;
        }
        return this.hashCode() == other.hashCode();
    }
}
