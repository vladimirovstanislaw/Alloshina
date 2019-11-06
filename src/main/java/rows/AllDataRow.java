package rows;

public class AllDataRow {
	private String id;
	private String name;
	private String LeftOvers;
	private int price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLeftOvers() {
		return LeftOvers;
	}

	public void setLeftOvers(String leftOvers) {
		LeftOvers = leftOvers;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "AllDataRow [id=" + id + ", name=" + name + ", LeftOvers=" + LeftOvers + ", price=" + price + "]";
	}

}
