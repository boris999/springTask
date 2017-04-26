package playground;

public class Robot {
 private String name;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Robot(String name) {
	super();
	this.name = name;
}

@Override
public String toString() {
	return "Robot [name=" + name + "]";
}
 
 
}
