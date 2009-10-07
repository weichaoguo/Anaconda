/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2008 INRIA/University of Nice-Sophia Antipolis
 * Contact: proactive@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version
 * 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * ################################################################
 * $PROACTIVE_INITIAL_DEV$
 */
package org.ow2.proactive.scheduler.common.task.dataspaces;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Proxy;
import org.objectweb.proactive.annotation.PublicAPI;


/**
 * OutputSelector is a couple of {@link FileSelector} and {@link OutputAccessMode}
 *
 * @author The ProActive Team
 * @since ProActive Scheduling 1.1
 */
@PublicAPI
@Entity
@Table(name = "OUTPUT_SELECTOR")
@AccessType("field")
@Proxy(lazy = false)
public class OutputSelector implements Serializable {

    @Id
    @GeneratedValue
    protected long hId;

    @Cascade(CascadeType.ALL)
    @OneToOne(fetch = FetchType.EAGER, targetEntity = FileSelector.class)
    private FileSelector outputFiles = null;

    @Column(name = "OUTPUT_MODE")
    private OutputAccessMode mode;

    public OutputSelector() {
    }

    /**
     * Create a new instance of OutputSelector
     *
     * @param outputFiles
     * @param mode
     */
    public OutputSelector(FileSelector outputFiles, OutputAccessMode mode) {
        this.outputFiles = outputFiles;
        this.mode = mode;
    }

    /**
     * Get the outputFiles
     *
     * @return the outputFiles
     */
    public FileSelector getOutputFiles() {
        return outputFiles;
    }

    /**
     * Set the outputFiles value to the given outputFiles value
     *
     * @param outputFiles the outputFiles to set
     */
    public void setOutputFiles(FileSelector outputFiles) {
        this.outputFiles = outputFiles;
    }

    /**
     * Get the mode
     *
     * @return the mode
     */
    public OutputAccessMode getMode() {
        return mode;
    }

    /**
     * Set the mode value to the given mode value
     *
     * @param mode the mode to set
     */
    public void setMode(OutputAccessMode mode) {
        this.mode = mode;
    }

}