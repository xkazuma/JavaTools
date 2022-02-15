package jp.ac.doshisha.mil.x.tools;

import org.openjdk.jol.info.ClassLayout;

public class ObjectSizeCalculator {

    public static long getObjectSize(Object o) {
        return ClassLayout.parseInstance(o).instanceSize();
    }
}
