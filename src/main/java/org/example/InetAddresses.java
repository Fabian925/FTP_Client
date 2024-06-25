package org.roalter;

import java.util.regex.Pattern;

public class InetAddresses {

	public static boolean isValidInet4Address(String ip) {
		final String IPV4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";
		final Pattern IPv4_PATTERN = Pattern.compile(IPV4_REGEX);
		if (ip == null)
			return false;
		if (!IPv4_PATTERN.matcher(ip).matches())
			return false;

		// Validates that every 32-Byte segment is not over 255 or in the false format.
		String[] parts = ip.split("\\.");
		try {
			for (String segment: parts) {
				// Checks if the format is x.0.x.x not that x.01.x.x
				if (Integer.parseInt(segment) > 255 || (segment.length() > 1 && segment.startsWith("0")))
					return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
