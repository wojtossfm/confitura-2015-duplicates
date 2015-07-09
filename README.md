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
If objects don't overwrite hashCode and equals then the customHash static method will be used as a substitute.
It will most likely run slower due to using reflection and far more accessors.
Implementation might need to be changed depending on what is to be treated as a duplicate and what isn't.

Currently mixing superclasses with subclasses hasn't been tested and probably isn't supported.

Could reduce number of iterations performed through the list (the overwrite check) by performing it on the fly and
switching hashsets when a change from overwrite to mixed is detected, but decided that it's a minor change in comparison
to the whole problem and I already spent more time than I wanted to working on the contest problems.