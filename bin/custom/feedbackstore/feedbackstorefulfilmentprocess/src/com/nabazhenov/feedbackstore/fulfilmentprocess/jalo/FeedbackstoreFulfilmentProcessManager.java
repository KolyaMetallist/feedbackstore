/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.nabazhenov.feedbackstore.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.nabazhenov.feedbackstore.fulfilmentprocess.constants.FeedbackstoreFulfilmentProcessConstants;

public class FeedbackstoreFulfilmentProcessManager extends GeneratedFeedbackstoreFulfilmentProcessManager
{
	public static final FeedbackstoreFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (FeedbackstoreFulfilmentProcessManager) em.getExtension(FeedbackstoreFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
