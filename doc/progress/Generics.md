
# Protocol has generic type
1. target method type has an argument with primitive type
2. target method type has an argument with Concrete type, but we search for template Object
3. it's not enough to run overload resolution in case of fail

Callsite ambiguous cases: we can have both types(Object or concrete, Object or Primitive)

# Protocol has concrete type
1. target method has Object(template) type

# Notes
1. Protocol and matching class has different site variance

# Issues
1. Overload exists
2. There is only one appropriate overload
3. Typechecker
