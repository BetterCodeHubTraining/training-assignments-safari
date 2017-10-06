/** 
 * NOTICE: 
 * - This file has been heavily modified by the Software Improvement Group (SIG) to adapt it for training purposes
 * - The original file can be found here: https://github.com/jMonkeyEngine/jmonkeyengine
 * - All dependencies in this file have been stubbed, some methods and/or their implementations in this file have been omitted, modified or removed
 * 
 * ORIGINAL FILE HEADER: 
 */
/*
 * Copyright (c) 2009-2012 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package eu.sig.safari.exercises.unit_parameters.jmonkeyengine;

import java.nio.ByteBuffer;

abstract class ImageCodec {
    
    public static final int FLAG_F16 = 1, FLAG_F32 = 2, FLAG_GRAY = 4; //, FLAG_ALPHAONLY = 8, FLAG_SHAREDEXP = 16;
   
    protected final int bpp, type, maxAlpha, maxRed, maxGreen, maxBlue;
    protected final boolean isGray;

    public ImageCodec(int bpp, int flags, int maxAlpha, int maxRed, int maxGreen, int maxBlue) {
        this.bpp = bpp;
        this.isGray = (flags & FLAG_GRAY) != 0;
        this.type = flags & ~FLAG_GRAY;
        this.maxAlpha = maxAlpha;
        this.maxRed = maxRed;
        this.maxGreen = maxGreen;
        this.maxBlue = maxBlue;
    }

    public abstract void readComponents(ByteBuffer buf, int x, int y, int width, int offset, int[] components, byte[] tmp);
    
    public abstract void writeComponents(ByteBuffer buf, int x, int y, int width, int offset, int[] components, byte[] tmp);

}