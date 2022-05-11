package com.deeplake.exp1182.util;


import java.text.SimpleDateFormat;


public class CommonDef {
    //public static final ITag.INamedTag<EntityType<?>> BOSSES = bind("bosses");

    public static final String EMPTY = "";
    public static final String NEWLINE = "\n";
    public static final String MINECRAFT = "minecraft";

    public static final int INT_AS_FLOAT = 10000;
    public static final float JUMP_FACTOR_MJDS = (float) Math.sqrt(6);

    public static final int TICK_PER_SECOND = 20;
    public static final int TICK_PER_DAY = 24000;

    public static final int MAX_BUILD_HEIGHT = 255;
    public static final int WORLD_HEIGHT = 256;

    public static final float TEMP_ABOVE_COLD = 0.1f;
    public static final float TEMP_ABOVE_HOT = 1.9f;

    //for fgo skills
    public static final int SECOND_PER_TURN = 5;
    //for arknight skills
    public static final int METER_PER_BLOCK = 2;

    public static final int TICK_PER_TURN = SECOND_PER_TURN * TICK_PER_SECOND;

    public static final float DEG_TO_RAD = 0.017453292F;
    public static final int CHUNK_SIZE = 16;
    public static final int CHUNK_MAX = 15;
    public static final float CHUNK_CENTER = 7.5f;
    public static final int CHUNK_CENTER_INT = 7;

    public static int GUA_TYPES = 8;

    public static int G_EARTH = 0;
    public static int G_THUNDER = 1;
    public static int G_WATER = 2;
    public static int G_MOUNTAIN = 4;
    public static int G_LAKE = 3;
    public static int G_FIRE = 5;
    public static int G_WIND = 6;
    public static int G_SKY = 7;

    public static int MAX_AIR = 300;

    public static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");

    /** 1.12.2
     * Flag 1 will cause a block update. Flag 2 will send the change to clients. Flag 4 will prevent the block from
     * being re-rendered, if this is a client world. Flag 8 will force any re-renders to run on the main thread instead
     * of the worker pool, if this is a client world and flag 4 is clear. Flag 16 will prevent observers from seeing
     * this change. Flags can be OR-ed
     */
    public static class BlockFlags
    {
        public static int BLOCK_UPDATE = 1;
        public static int TO_CLIENT = 2;
        public static int CLIENT_DONT_RENDER = 4;
        public static int FORCE_RENDER = 8;
        public static int IGNORE_OB = 16;
    }

    public static int CHUNK_MASK = 15;
}
