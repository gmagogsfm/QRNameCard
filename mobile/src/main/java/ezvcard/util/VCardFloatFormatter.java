package ezvcard.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/*
 Copyright (c) 2012-2015, Michael Angstadt
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met: 

 1. Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer. 
 2. Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution. 

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * Formats floating-point values for vCards. Notably, this ensures that a "." is
 * used as decimal separator, no matter the locale.
 * @author Michael Angstadt
 */
@SuppressWarnings("serial")
public class VCardFloatFormatter extends DecimalFormat {
	/**
	 * Creates a new formatter with a max of 6 decimals.
	 */
	public VCardFloatFormatter() {
		this(6);
	}

	/**
	 * Creates a new formatter.
	 * @param decimals the max number of decimal places
	 */
	public VCardFloatFormatter(int decimals) {
		setMaximumFractionDigits(decimals);
		if (decimals > 0) {
			setMinimumFractionDigits(1);
		}

		//decimal separator differs by locale (e.g. Germany uses ",")
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		symbols.setMinusSign('-');
		setDecimalFormatSymbols(symbols);
	}
}
