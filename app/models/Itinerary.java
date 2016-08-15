package models;

public class Itinerary {
	
	Long duration;
	Long startTime;
	Long endTime;
	Long walkTime;
	Long transitTime;
	Long waitingTime;
	Long walkDistance;
	Integer transfers;
	
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Long getWalkTime() {
		return walkTime;
	}
	public void setWalkTime(Long walkTime) {
		this.walkTime = walkTime;
	}
	public Long getTransitTime() {
		return transitTime;
	}
	public void setTransitTime(Long transitTime) {
		this.transitTime = transitTime;
	}
	public Long getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(Long waitingTime) {
		this.waitingTime = waitingTime;
	}
	public Long getWalkDistance() {
		return walkDistance;
	}
	public void setWalkDistance(Long walkDistance) {
		this.walkDistance = walkDistance;
	}
	public Integer getTransfers() {
		return transfers;
	}
	public void setTransfers(Integer transfers) {
		this.transfers = transfers;
	}

}
