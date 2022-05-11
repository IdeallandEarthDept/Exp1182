package com.deeplake.exp1182.util;

public class MJDSDefine {

    public enum EnumEgo{
        NONE(0),
        POPLON(1),
        APHRODITE(2),
        GRADUATE(3),
        BOTH_DEAD(4);

        final int value;

        EnumEgo(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public boolean is(int _v){
            return value == _v;
        }

        public static boolean isNormalEgo(int status)
        {
            return POPLON.is(status) || APHRODITE.is(status);
        }

        public static EnumEgo fromInt(int status)
        {
            for (EnumEgo ego:values()) {
                if (ego.is(status))
                {
                    return ego;
                }
            }
            return EnumEgo.NONE;
        }
    }

}
