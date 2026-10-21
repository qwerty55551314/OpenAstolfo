package astolfo.event.bus;

import astolfo.event.Event;
import astolfo.event.Listener;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public enum InvocationStrategy {
   METHOD_REFLECTIVE_INVOCATION(MemberKind.METHOD) {
      @Override
      public <O, E extends Event> Listener<E> createDispatcher(Class<E> class2, O object, AnnotatedElement annotatedElement) {
         Method method = (Method)annotatedElement;
         method.setAccessible(true);
         return event -> {
            try {
               method.invoke(object, event);
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException illegalaccessexception) {
               ReflectionErrors.rethrowReflectionException(illegalaccessexception);
            }
         };
      }
   },
   METHOD_LAMBDA_INVOCATION(MemberKind.METHOD) {
      @Override
      public <O, E extends Event> Listener<E> createDispatcher(Class<E> class2, O object, AnnotatedElement annotatedElement) {
         Method method = (Method)annotatedElement;
         method.setAccessible(true);

         try {
            Lookup lookup1 = InvocationStrategy.LOOKUP.in(object.getClass());
            MethodHandle methodhandle = null;
            int i = method.getModifiers();
            boolean flag1 = Modifier.isPrivate(i);
            if (flag1) {
               if (InvocationStrategy.HAS_TRUSTED_LOOKUP) {
                  methodhandle = Modifier.isStatic(i) ? lookup1.unreflect(method) : lookup1.unreflectSpecial(method, object.getClass());
               }
            } else {
               boolean flag2 = Modifier.isProtected(i);
               if (flag2) {
                  if (InvocationStrategy.HAS_TRUSTED_LOOKUP) {
                     methodhandle = lookup1.unreflect(method);
                  }
               } else if (Modifier.isPublic(i)) {
                  methodhandle = lookup1.unreflect(method);
               } else {
                  methodhandle = lookup1.unreflect(method);
               }
            }

            if (methodhandle != null) {
               MethodType methodtype1 = MethodType.methodType(void.class, class2);
               Listener listener;
               if (Modifier.isStatic(i)) {
                  MethodType methodtype = MethodType.methodType(Listener.class);
                  listener = (Listener)LambdaMetafactory.metafactory(lookup1, "accept", methodtype, methodtype1.erase(), methodhandle, methodtype1)
                     .dynamicInvoker()
                     .invoke();
               } else {
                  MethodType methodtype2 = MethodType.methodType(Listener.class, object.getClass());
                  listener = (Listener)LambdaMetafactory.metafactory(lookup1, "accept", methodtype2, methodtype1.erase(), methodhandle, methodtype1)
                     .dynamicInvoker()
                     .invoke((Object)object);
               }

               return listener;
            } else {
               throw new RuntimeException("Unable to do lookup on method \"" + method.getName() + "\".");
            }
         } catch (Throwable throwable) {
            ReflectionErrors.rethrowUnchecked(throwable);
            return null;
         }
      }
   },
   FIELD_REFLECTIVE_INVOCATION(MemberKind.FIELD) {
      @Override
      public <O, E extends Event> Listener<E> createDispatcher(Class<E> class2, O object, AnnotatedElement annotatedElement) {
         Field field1 = (Field)annotatedElement;
         field1.setAccessible(true);

         try {
            return (Listener<E>)field1.get(object);
         } catch (IllegalAccessException | IllegalArgumentException illegalargumentexception) {
            ReflectionErrors.rethrowReflectionException(illegalargumentexception);
            return null;
         }
      }
   };

   public static final Lookup LOOKUP;
   public static final boolean HAS_TRUSTED_LOOKUP;
   private MemberKind memberKind;
   private static final InvocationStrategy[] VALUES = new InvocationStrategy[]{
      METHOD_REFLECTIVE_INVOCATION, METHOD_LAMBDA_INVOCATION, FIELD_REFLECTIVE_INVOCATION
   };

   private InvocationStrategy(MemberKind memberKind) {
      this.memberKind = memberKind;
   }

   public abstract <O, E extends Event> Listener<E> createDispatcher(Class<E> var1, O var2, AnnotatedElement var3);

   public MemberKind getMemberKind() {
      return this.memberKind;
   }

   static {
      Lookup lookup;
      boolean flag;
      try {
         Field field = Lookup.class.getDeclaredField("IMPL_LOOKUP");
         field.setAccessible(true);
         lookup = (Lookup)field.get(null);
         flag = true;
      } catch (ReflectiveOperationException reflectiveoperationexception) {
         lookup = MethodHandles.lookup();
         flag = false;
      }

      LOOKUP = lookup;
      HAS_TRUSTED_LOOKUP = flag;
   }
}
