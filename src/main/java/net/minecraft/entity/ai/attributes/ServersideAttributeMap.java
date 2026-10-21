package net.minecraft.entity.ai.attributes;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.server.management.LowerStringMap;

public class ServersideAttributeMap extends BaseAttributeMap {
   private final Set<IAttributeInstance> attributeInstanceSet = Sets.newHashSet();
   public final Map<String, IAttributeInstance> descriptionToAttributeInstanceMap = new LowerStringMap<>();

   public ModifiableAttributeInstance getAttributeInstance(IAttribute attribute) {
      return (ModifiableAttributeInstance)super.getAttributeInstance(attribute);
   }

   public ModifiableAttributeInstance getAttributeInstanceByName(String attributeName) {
      IAttributeInstance iattributeinstance = super.getAttributeInstanceByName(attributeName);
      if (iattributeinstance == null) {
         iattributeinstance = this.descriptionToAttributeInstanceMap.get(attributeName);
      }

      return (ModifiableAttributeInstance)iattributeinstance;
   }

   @Override
   public IAttributeInstance registerAttribute(IAttribute attribute) {
      IAttributeInstance iattributeinstance = super.registerAttribute(attribute);
      if (attribute instanceof RangedAttribute) {
         String s = ((RangedAttribute)attribute).getDescription();
         if (s != null) {
            this.descriptionToAttributeInstanceMap.put(((RangedAttribute)attribute).getDescription(), iattributeinstance);
         }
      }

      return iattributeinstance;
   }

   @Override
   public IAttributeInstance func_180376_c(IAttribute attribute) {
      return new ModifiableAttributeInstance(this, attribute);
   }

   @Override
   public void func_180794_a(IAttributeInstance instance) {
      boolean flag = instance.getAttribute().getShouldWatch();
      if (flag) {
         this.attributeInstanceSet.add(instance);
      }

      for (IAttribute iattribute : super.field_180377_c.get(instance.getAttribute())) {
         ModifiableAttributeInstance modifiableattributeinstance = this.getAttributeInstance(iattribute);
         if (modifiableattributeinstance != null) {
            modifiableattributeinstance.flagForUpdate();
         }
      }
   }

   public Set<IAttributeInstance> getAttributeInstanceSet() {
      return this.attributeInstanceSet;
   }

   public Collection<IAttributeInstance> getWatchedAttributes() {
      HashSet hashset = Sets.newHashSet();

      for (IAttributeInstance iattributeinstance : this.getAllAttributes()) {
         if (iattributeinstance.getAttribute().getShouldWatch()) {
            hashset.add(iattributeinstance);
         }
      }

      return hashset;
   }
}
