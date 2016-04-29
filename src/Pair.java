
/*
 * Pair
 * 
 * a generic holder that stores any pair of generic object types
 */
class Pair<T>
 {
	
    //two generic types
    private T x;
    private T y;

    
    //setters for generic types
    
    public void setX ( T v )
    {
        x = v;
    }

    public void setY( T v)
    {
    	y = v;
    }
    
    //getters for generic types
    
    public T getX()
    {
        return x;
    }
    
    public T getY()
    {
    	return y;
    }
 }