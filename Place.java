
public class Place {
	protected String place;
	protected Place next;
	
	public Place(String place) {
		this.place = place;
		this.next = null;
	}
	
	public String getPlace() {return place;};
	public Place getNext() {return next;}
	
	public void setPlace(String place) {this.place = place;}
	public void setNext(Place next) {this.next = next;}
	
	@Override
	public String toString() {
		return place;
	}
}
