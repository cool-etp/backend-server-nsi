package org.cooletp.server.nsi.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "nsi_okei")
public class Okei extends BaseNsi {

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "section_code")
    private int sectionCode;

    @Column(name = "section_name")
    private String sectionName;

    @Column(name = "group_code")
    private int groupCode;

    @Column(name = "group_name")
    private String groupName;
}
