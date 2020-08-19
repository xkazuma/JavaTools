package jp.ac.doshisha.mil.x.tools.datatype;

import lombok.Data;

@Data(staticConstructor = "of")
public class Pair<A, B> {
    private final A left;
    private final B right;
}
