DIY ArrayList

Write your own implementation of ArrayList based on an array.

class DIYarrayList<T> implements List<T>{...}

Check that methods from java.util.Collections work on it:

Collections.addAll(Collection<? super T> c, T... elements)

Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)

Collections.static <T> void sort(List<T> list, Comparator<? super T> c)

1) Check on collections with 20 or more elements.

2) DIYarrayList must implement ONLY ONE interface - List.

3) If the method is not implemented, it must throw an UnsupportedOperationException.
