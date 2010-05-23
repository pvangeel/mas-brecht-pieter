package pclabs3.layer.physical;

import framework.layer.physical.command.Command;
import framework.layer.physical.entities.StaticResource;

public class Site extends StaticResource<SiteLocation> {

	@Override
	protected Command<? extends StaticResource<SiteLocation>> loadFailSafeCommand() {
		// TODO Auto-generated method stub
		return null;
	}

}
