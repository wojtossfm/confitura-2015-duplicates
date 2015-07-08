# [Confitura 2015](http://tech.viacom.com/warsawsdc/confitura2015/)
## Duplicates

You have *very big* list of elements. Please provide best solution to detect and remove duplicated elements.

Please provide a solution and **comments** about its benefits and drawbacks. Please give us complexity (`O(n)`, `O(n^2)`, `O(ln(n))`, ...). Please think about custom classes like:

```java
class Person {
    String name;
    int age;
}
```

You can check contest bye-laws [here](http://tech.viacom.com/warsawsdc/confitura2015/Regulamin_konkurs_Viacom_programmer_adventure_2015.pdf).

Check out our Confitura 2015 site [here](http://tech.viacom.com/warsawsdc/confitura2015/)

We are hiring! Visit our [career site](http://tech.viacom.com/careers/).

## Solution

Assuming that the hashCode/equals methods run in O(1) time then the deduplication solution runs in O(n) time.
If objects don't overwrite hashCode then the customHash static method will be used as a substitute and it will run slower
due to using reflection and far more accessors.

Currently mixing superclasses with subclasses hasn't been tested and probably isn't supported.