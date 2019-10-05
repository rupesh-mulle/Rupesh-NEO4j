package com.lowes.assignment.model;

import com.datastax.driver.core.DataType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@AllArgsConstructor
@Table("product")
public class Product implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKey()
    @CassandraType(type = DataType.Name.UUID)
	@Id
    private UUID id;
	
	@Column("name")
    private @NonNull String name;
    
	@Column("category")
    private @NonNull String category;
    
	public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Product() {
        id = UUID.randomUUID();
    }

}
