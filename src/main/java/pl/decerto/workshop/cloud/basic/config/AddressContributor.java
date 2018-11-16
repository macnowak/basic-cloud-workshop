package pl.decerto.workshop.cloud.basic.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class AddressContributor implements InfoContributor {

	@Override
	public void contribute(Info.Builder builder) {
		try {
			builder
				.withDetail("local.hostAddress", InetAddress.getLocalHost().getHostAddress())
				.withDetail("local.hostName", InetAddress.getLocalHost().getHostName())
				.withDetail("loopback.hostAddress", InetAddress.getLoopbackAddress().getHostAddress())
				.withDetail("loopback.hostName", InetAddress.getLoopbackAddress().getHostName());
		} catch (UnknownHostException e) {
			log.error("Error getting address info", e);
		}

	}
}
