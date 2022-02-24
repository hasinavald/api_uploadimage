package com.signal.api.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name="signals")
public class Signal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@Column(name="image")
	@Type(type="org.hibernate.type.BinaryType")
	private byte[] image;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "type", 
				joinColumns = @JoinColumn(name = "signal_id"), 
				inverseJoinColumns = @JoinColumn(name = "typeSignal_id"))
	private List<TypeSignal> typeSignal;
	
	private String description;

	private String status;
	
	private float latitude;
	
	private float longitude;
	
	private String region = null;

	private String username;

	private Date date;
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

	public List<TypeSignal> getTypeSignal() {
		return typeSignal;
	}

	public void setTypeSignal(List<TypeSignal> typeSignal) {
		this.typeSignal = typeSignal;
	}

	public String getDescription() {
		return description;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String nomRegion) {
		this.region = nomRegion;
	}

	public String getUsername(){
		return this.username;
	}

	public void setUsername(String username){
		this.username = username;
	}
	


}
