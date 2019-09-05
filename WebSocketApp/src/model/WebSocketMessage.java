package model;


public class WebSocketMessage<T> { 
	
	private String type;
	private T payload;

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public T getPayload() {
		return payload;
	}


	public void setPayload(T payload) {
		this.payload = payload;
	}

}
