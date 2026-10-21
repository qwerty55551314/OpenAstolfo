package astolfo.lua;

import astolfo.ClientAccess;
import astolfo.lua.world.Angles;
import astolfo.lua.world.Biome;
import astolfo.lua.world.Block;
import astolfo.lua.world.BoundingBox;
import astolfo.lua.world.Burning;
import astolfo.lua.world.DisplayName;
import astolfo.lua.world.Entities;
import astolfo.lua.world.Facing;
import astolfo.lua.world.Health;
import astolfo.lua.world.HeldItem;
import astolfo.lua.world.HurtTime;
import astolfo.lua.world.Inventory;
import astolfo.lua.world.IsBot;
import astolfo.lua.world.IsEnemy;
import astolfo.lua.world.IsFriend;
import astolfo.lua.world.IsInvisible;
import astolfo.lua.world.IsPlayer;
import astolfo.lua.world.IsPotionActive;
import astolfo.lua.world.IsSneaking;
import astolfo.lua.world.MaxHealth;
import astolfo.lua.world.Name;
import astolfo.lua.world.Position;
import astolfo.lua.world.PrevPosition;
import astolfo.lua.world.RefreshChunks;
import astolfo.lua.world.Remove;
import astolfo.lua.world.Riding;
import astolfo.lua.world.SetTimer;
import astolfo.lua.world.Sprinting;
import astolfo.lua.world.TicksExisted;
import astolfo.lua.world.Timer;
import astolfo.lua.world.WidthHeight;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.Entity;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

public class WorldLibrary extends TwoArgFunction implements ClientAccess {
   public static final Map<Integer, Entity> entitiesById = new HashMap<>();
   private static final ArrayList<LuaValue> entityIdList = new ArrayList<>();
   public static final VarArgFunction entitiesFunction = new Entities();
   public static final VarArgFunction inventoryFunction = new Inventory();
   public static final ThreeArgFunction blockFunction = new Block();
   public static final OneArgFunction nameFunction = new Name();
   public static final OneArgFunction isBotFunction = new IsBot();
   public static final OneArgFunction isFriendFunction = new IsFriend();
   public static final OneArgFunction isEnemyFunction = new IsEnemy();
   public static final OneArgFunction heldItemFunction = new HeldItem();
   public static final OneArgFunction hurtTimeFunction = new HurtTime();
   public static final ZeroArgFunction refreshChunksFunction = new RefreshChunks();
   public static final OneArgFunction setTimerFunction = new SetTimer();
   public static final ZeroArgFunction timerFunction = new Timer();
   public static final OneArgFunction displayNameFunction = new DisplayName();
   public static final OneArgFunction healthFunction = new Health();
   public static final OneArgFunction sprintingFunction = new Sprinting();
   public static final OneArgFunction maxHealthFunction = new MaxHealth();
   public static final OneArgFunction ticksExistedFunction = new TicksExisted();
   public static final OneArgFunction ridingFunction = new Riding();
   public static final VarArgFunction anglesFunction = new Angles();
   public static final VarArgFunction boundingBoxFunction = new BoundingBox();
   public static final VarArgFunction positionFunction = new Position();
   public static final VarArgFunction prevPositionFunction = new PrevPosition();
   public static final VarArgFunction widthHeightFunction = new WidthHeight();
   public static final OneArgFunction isPlayerFunction = new IsPlayer();
   public static final OneArgFunction facingFunction = new Facing();
   public static final ZeroArgFunction biomeFunction = new Biome();
   public static final OneArgFunction isSneakingFunction = new IsSneaking();
   public static final TwoArgFunction isPotionActiveFunction = new IsPotionActive();
   public static final OneArgFunction isInvisibleFunction = new IsInvisible();
   public static final OneArgFunction burningFunction = new Burning();
   public static final OneArgFunction removeFunction = new Remove();

   @Override
   public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
      LuaTable luatable = LuaValue.tableOf();
      luatable.set("timer", timerFunction);
      luatable.set("set_timer", setTimerFunction);
      luatable.set("refresh_chunks", refreshChunksFunction);
      luatable.set("entities", entitiesFunction);
      luatable.set("block", blockFunction);
      luatable.set("name", nameFunction);
      luatable.set("display_name", displayNameFunction);
      luatable.set("held_item", heldItemFunction);
      luatable.set("hurt_time", hurtTimeFunction);
      luatable.set("health", healthFunction);
      luatable.set("sprinting", sprintingFunction);
      luatable.set("facing", facingFunction);
      luatable.set("angles", anglesFunction);
      luatable.set("bounding_box", boundingBoxFunction);
      luatable.set("biome", biomeFunction);
      luatable.set("max_health", maxHealthFunction);
      luatable.set("inventory", inventoryFunction);
      luatable.set("ticks_existed", ticksExistedFunction);
      luatable.set("is_player", isPlayerFunction);
      luatable.set("width_height", widthHeightFunction);
      luatable.set("is_sneaking", isSneakingFunction);
      luatable.set("is_potion_active", isPotionActiveFunction);
      luatable.set("is_invisible", isInvisibleFunction);
      luatable.set("burning", burningFunction);
      luatable.set("is_bot", isBotFunction);
      luatable.set("is_friend", isFriendFunction);
      luatable.set("is_enemy", isEnemyFunction);
      luatable.set("riding", ridingFunction);
      luatable.set("position", positionFunction);
      luatable.set("prev_position", prevPositionFunction);
      luatable.set("remove", removeFunction);
      luaValue2.set("world", luatable);
      return luatable;
   }

   public static ArrayList getEntityIdList() {
      return entityIdList;
   }

   public static boolean F() {
      return true;
   }
}
