/*
 * SPDX-License-Identifier: Apache-1.1
 *
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.
 * Copyright (c) 2010 Dmitry Naumenko (dm.naumenko@gmail.com)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowledgement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgement may appear in the software itself,
 *    if and wherever such third-party acknowledgements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package difflib;

import java.util.List;

/**
 * Describes the add-delta between original and revised texts.
 * 
 * @author <a href="dm.naumenko@gmail.com">Dmitry Naumenko</a>
 * @param T
 *            The type of the compared elements in the 'lines'.
 */
public class InsertDelta<T> extends Delta<T> {

	/**
	 * Creates an insert delta with the two given chunks.
	 * 
	 * @param original
	 *            The original chunk. Must not be {@code null}.
	 * @param revised
	 *            The original chunk. Must not be {@code null}.
	 */
	public InsertDelta(Chunk<T> original, Chunk<T> revised) {
		super(original, revised);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws PatchFailedException
	 */
	@Override
	public void applyTo(List<T> target) throws PatchFailedException {
		verify(target);
		int position = this.getOriginal().getPosition();
		List<T> lines = this.getRevised().getLines();
		for (int i = 0; i < lines.size(); i++) {
			target.add(position + i, lines.get(i));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void restore(List<T> target) {
		int position = getRevised().getPosition();
		int size = getRevised().size();
		for (int i = 0; i < size; i++) {
			target.remove(position);
		}
	}

	@Override
	public void verify(List<T> target) throws PatchFailedException {
		if (getOriginal().getPosition() > target.size()) {
			throw new PatchFailedException("Incorrect patch for delta: "
					+ "delta original position > target size");
		}

	}

	public TYPE getType() {
		return Delta.TYPE.INSERT;
	}

	@Override
	public String toString() {
		return "[InsertDelta, position: " + getOriginal().getPosition()
				+ ", position: " + getRevised().getPosition()
				+ ", lines: " + getRevised().getLines() + "]";
	}
}
