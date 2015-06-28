/**
 * Copyright (c) 2005, Regents of the University of California
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * Neither the name of the University of California, Los Angeles nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Creation date: Nov 3, 2005
 */

package avrora.jintgen.isdl;

import avrora.cck.util.Util;
import avrora.jintgen.isdl.parser.Token;
import avrora.jintgen.jigir.JIGIRTypeEnv;
import avrora.jintgen.types.Type;
import avrora.jintgen.types.TypeCon;
import avrora.jintgen.types.TypeEnv;
import avrora.jintgen.types.TypeRef;

/**
 * @author Ben L. Titzer
 */
public class EnumTypeRef extends TypeRef
{

    EnumDecl decl;


    public EnumTypeRef(Token t)
    {
        super(t);
    }


    public EnumTypeRef(Type t)
    {
        super(t);
        TypeCon tc = t.getTypeCon();
        if (tc instanceof JIGIRTypeEnv.TYPE_enum)
        {
            decl = ((JIGIRTypeEnv.TYPE_enum) tc).decl;
        } else
        {
            throw Util
                    .failure("Cannot create enum type reference to type " + t);
        }
    }


    @Override
    public Type resolve(TypeEnv te)
    {
        Type t = super.resolve(te);
        TypeCon tc = t.getTypeCon();
        if (tc instanceof JIGIRTypeEnv.TYPE_enum)
        {
            decl = ((JIGIRTypeEnv.TYPE_enum) tc).decl;
            return t;
        }
        // default case: not the right type class
        te.ERROR.ExpectedTypeClass(this, "enum");
        return t;
    }


    public EnumDecl getEnumDecl()
    {
        if (decl == null)
            throw Util.failure("Enum type reference not resolved at "
                    + tcName.getSourcePoint());
        return decl;
    }
}
