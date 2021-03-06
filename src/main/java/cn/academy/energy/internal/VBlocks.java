/**
* Copyright (c) Lambda Innovation, 2013-2016
* This file is part of the AcademyCraft mod.
* https://github.com/LambdaInnovation/AcademyCraft
* Licensed under GPLv3, see project root for more information.
*/
package cn.academy.energy.internal;

import cn.academy.energy.api.block.*;
import cn.lambdalib.util.generic.MathUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 */
public class VBlocks {
    
    static abstract class VBlock<T extends IWirelessTile> {
        
        protected final int x, y, z;
        protected final boolean ignoreChunk;
        
        public VBlock(TileEntity te, boolean _ignoreChunk) {
            x = te.xCoord;
            y = te.yCoord;
            z = te.zCoord;
            ignoreChunk = _ignoreChunk;
        }
        
        public VBlock(NBTTagCompound tag, boolean _ignoreChunk) {
            x = tag.getInteger("x");
            y = tag.getInteger("y");
            z = tag.getInteger("z");
            ignoreChunk = _ignoreChunk;
        }
        
        public double distSq(VBlock another) {
            return MathUtils.distanceSq(another.x, another.y, another.z, x, y, z);
        }
        
        public boolean isLoaded(World world) {
            return world.getChunkProvider().chunkExists(x >> 4, z >> 4);
        }
        
        public T get(World world) {
            if(!ignoreChunk && !isLoaded(world))
                return null;
            if(world == null)
                return null;
            
            world.getChunkProvider().loadChunk(x >> 4, z >> 4);
            
            TileEntity te = world.getTileEntity(x, y, z);
            if(te == null || !isAcceptable(te)) {
                return null;
            }
            return (T) te;
        }
        
        public NBTTagCompound toNBT() {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("x", x);
            tag.setInteger("y", y);
            tag.setInteger("z", z);
            return tag;
        }
        
        @Override
        public int hashCode() {
            return x ^ y ^ z;
        }
        
        @Override
        public boolean equals(Object obj) {
            if(obj.getClass() != this.getClass()) {
                return false;
            }
            VBlock vb = (VBlock) obj;
            return vb.x == x && vb.y == y && vb.z == z;
        }
        
        @Override
        public String toString() {
            return getClass().getSimpleName() + "[" + x + ", " + y + ", " + z + "]";
        }
        
        protected abstract boolean isAcceptable(TileEntity tile);
        
    }
    
    static class VWMatrix extends VBlock<IWirelessMatrix> {

        public VWMatrix(IWirelessMatrix te) {
            super((TileEntity) te, true);
        }
        
        public VWMatrix(NBTTagCompound tag) {
            super(tag, true);
        }

        @Override
        protected boolean isAcceptable(TileEntity tile) {
            return tile instanceof IWirelessMatrix;
        }
        
    }
    
    static class VWNode extends VBlock<IWirelessNode> {
        
        public VWNode(IWirelessNode te) {
            super((TileEntity) te, false);
        }
        
        public VWNode(NBTTagCompound tag) {
            super(tag, false);
        }

        @Override
        protected boolean isAcceptable(TileEntity tile) {
            return tile instanceof IWirelessNode;
        }
        
    }
    
    static class VNNode extends VBlock<IWirelessNode> {
        public VNNode(IWirelessNode te) {
            super((TileEntity) te, true);
        }
        
        public VNNode(NBTTagCompound tag) {
            super(tag, true);
        }

        @Override
        protected boolean isAcceptable(TileEntity tile) {
            return tile instanceof IWirelessNode;
        }
    }
    
    static class VNGenerator extends VBlock<IWirelessGenerator> {
        public VNGenerator(IWirelessGenerator te) {
            super((TileEntity) te, true);
        }
        
        public VNGenerator(NBTTagCompound tag) {
            super(tag, true);
        }

        @Override
        protected boolean isAcceptable(TileEntity tile) {
            return tile instanceof IWirelessGenerator;
        }
    }
    
    static class VNReceiver extends VBlock<IWirelessReceiver> {
        public VNReceiver(IWirelessReceiver te) {
            super((TileEntity) te, true);
        }
        
        public VNReceiver(NBTTagCompound tag) {
            super(tag, true);
        }

        @Override
        protected boolean isAcceptable(TileEntity tile) {
            return tile instanceof IWirelessReceiver;
        }
    }
    
}
