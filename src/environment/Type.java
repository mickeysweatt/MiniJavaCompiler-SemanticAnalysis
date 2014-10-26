package environment;

/**
 * Created by admin on 10/23/14.
 */
public interface Type
{
    public String typeName();

    boolean subtype(Type rhs);

}

