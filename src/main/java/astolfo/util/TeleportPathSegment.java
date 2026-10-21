package astolfo.util;

import astolfo.ClientAccess;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWall;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public class TeleportPathSegment implements ClientAccess {
   private static final Minecraft minecraft = Minecraft.getMinecraft();
   private static final Vector3[] neighborOffsets = new Vector3[]{
      new Vector3(1.0, 0.0, 0.0), new Vector3(-1.0, 0.0, 0.0), new Vector3(0.0, 0.0, 1.0), new Vector3(0.0, 0.0, -1.0)
   };
   private final Vector3 start;
   private final Vector3 goal;
   private final ArrayList<PathNode> closedNodes = new ArrayList<>();
   private final ArrayList<PathNode> openNodes = new ArrayList<>();
   private ArrayList<Vector3> path = new ArrayList<>();

   public TeleportPathSegment(Vector3 vector3, Vector3 vector32) {
      this.start = vector3.add(0.0, 0.0, 0.0).floor();
      this.goal = vector32.add(0.0, 0.0, 0.0).floor();
   }

   public static boolean isWalkable(Vector3 vector3) {
      boolean flag = false;
      BlockPos blockpos = new BlockPos(vector3);
      return !isSolidBlock(blockpos) && !isSolidBlock(blockpos.add(0, 1, 0)) ? canStandOn(blockpos.add(0, -1, 0)) : false;
   }

   private static boolean isSolidBlock(BlockPos blockPos) {
      Block block = minecraft.theWorld.getBlockState(blockPos).getBlock();
      return block.isFullBlock()
         || block instanceof BlockSlab
         || block instanceof BlockStairs
         || block instanceof BlockCactus
         || block instanceof BlockChest
         || block instanceof BlockEnderChest
         || block instanceof BlockSkull
         || block instanceof BlockPane
         || block instanceof BlockFence
         || block instanceof BlockWall
         || block instanceof BlockGlass
         || block instanceof BlockPistonBase
         || block instanceof BlockPistonExtension
         || block instanceof BlockPistonMoving
         || block instanceof BlockStainedGlass
         || block instanceof BlockTrapDoor;
   }

   private static boolean canStandOn(BlockPos blockPos) {
      Block block = minecraft.theWorld.getBlockState(blockPos).getBlock();
      boolean flag = block instanceof BlockFence;
      if (!flag) {
         boolean flag1 = block instanceof BlockWall;
         if (!flag1) {
            return true;
         }
      }

      return false;
   }

   public ArrayList<Vector3> getPath() {
      return this.path;
   }

   public void findPathDefault() {
      this.findPathWithLimits(1000, 4);
   }

   public void findPathWithLimits(int i, int j) {
      this.path.clear();
      this.openNodes.clear();
      ArrayList arraylist = new ArrayList();
      boolean flag = false;
      Vector3 vector3 = this.start;
      arraylist.add(vector3);
      Vector3[] avector3 = neighborOffsets;
      this.openNodes.add(new PathNode(vector3, null, arraylist, vector3.distanceSq(this.goal), 0.0, 0.0));

      label51:
      for (int i1 = 0; i1 < i; i1++) {
         ArrayList arraylist1 = this.openNodes;
         int j1 = arraylist1.size();
         arraylist1.sort(new PathNodeComparator());
         int k = 0;
         if (j1 == 0) {
            break;
         }

         for (int l = 0; l < j1; l++) {
            PathNode pathnode = (PathNode)arraylist1.get(l);
            if (++k <= j) {
               arraylist1.remove(pathnode);
               this.closedNodes.add(pathnode);
               Vector3 vector32 = pathnode.getPosition();
               int i1x = avector3.length;

               for (int j1x = 0; j1x < i1x; j1x++) {
                  Vector3 vector33 = vector32.add(avector3[j1x]).floor();
                  if (isWalkable(vector33) && this.tryAddNode(pathnode, vector33, 0.0)) {
                     break label51;
                  }
               }

               Vector3 vector34 = vector32.add(0.0, 1.0, 0.0).floor();
               Vector3 vector31;
               if (isWalkable(vector34) && this.tryAddNode(pathnode, vector34, 0.0)
                  || isWalkable(vector31 = vector32.add(0.0, -1.0, 0.0).floor()) && this.tryAddNode(pathnode, vector31, 0.0)) {
                  break label51;
               }
               continue;
            }
         }
      }

      this.closedNodes.sort(new PathNodeComparator());
      this.path = this.closedNodes.get(0).getPath();
   }

   public PathNode findNode(Vector3 vector3) {
      ArrayList arraylist = this.closedNodes;
      int i = arraylist.size();

      for (int j = 0; j < i; j++) {
         PathNode pathnode = (PathNode)arraylist.get(j);
         Vector3 vector31 = pathnode.getPosition();
         if (vector31.getX() == vector3.getX() && vector31.getY() == vector3.getY() && vector31.getZ() == vector3.getZ()) {
            return pathnode;
         }
      }

      ArrayList arraylist1 = this.openNodes;
      int k = arraylist1.size();

      for (int l = 0; l < k; l++) {
         PathNode pathnode1 = (PathNode)arraylist1.get(l);
         Vector3 vector31 = pathnode1.getPosition();
         if (vector31.getX() == vector3.getX() && vector31.getY() == vector3.getY() && vector31.getZ() == vector3.getZ()) {
            return pathnode1;
         }
      }

      return null;
   }

   public boolean tryAddNode(PathNode pathNode, Vector3 vector3, double d) {
      PathNode pathnode = this.findNode(vector3);
      double d0 = d;
      if (pathNode != null) {
         d0 = d + pathNode.getTotalCost();
      }

      Vector3 vector31 = this.goal;
      ArrayList arraylist = pathNode.getPath();
      if (pathnode == null) {
         if (vector3.getX() == vector31.getX() && vector3.getY() == vector31.getY() && vector3.getZ() == vector31.getZ()) {
            this.path.clear();
            this.path = arraylist;
            this.path.add(vector3);
            return true;
         }

         arraylist.add(vector3);
         this.openNodes.add(new PathNode(vector3, pathNode, arraylist, vector3.distanceSq(vector31), d, d0));
      } else {
         double d1;
         int i = (d1 = pathnode.getStepCost() - d) == 0.0 ? 0 : (d1 < 0.0 ? -1 : 1);
         if (i > 0) {
            arraylist.add(vector3);
            pathnode.setPosition(vector3);
            pathnode.setParent(pathNode);
            pathnode.setPath(arraylist);
            pathnode.setDistanceToGoalSq(vector3.distanceSq(vector31));
            pathnode.setStepCost(d);
            pathnode.setTotalCost(d0);
         }
      }

      return false;
   }
}
