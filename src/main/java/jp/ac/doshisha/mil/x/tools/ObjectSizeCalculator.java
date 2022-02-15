package jp.ac.doshisha.mil.x.tools;

import org.openjdk.jol.info.GraphLayout;

public class ObjectSizeCalculator {

    public static long getObjectSize(Object o) {
        return GraphLayout.parseInstance(o).totalSize();
    }
}
