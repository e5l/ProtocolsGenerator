
# Protocol has generic type
1. target method type has an argument with primitive type
2. target method type has an argument with Concrete type, but we search for template Object
3. it's not enough to run overload resolution in case of fail

Callsite ambiguous cases: we can have both types(Object or concrete, Object or Primitive)

# Protocol has concrete type
1. target method has Object(template) type

Possible solutions:
1. Try to find method and if exception was thrown, run overload resolution.
2. Check if protocol class(at compile time) or target class(at runtime) has generic parameters, but we can't be sure. We can have method with generic parameters.
3. Bruteforce: always run overload resolution

# Design issues
1. Protocol and matching class has different site variance
