package model;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;


@SuppressWarnings("rawtypes")
public class Message<T> extends WebSocketMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String type = "message";
	private ObjectId id;
	private String contentType;
	private T content;
	private String sender;
	private String receiver;
	private Date receivedDate;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

}