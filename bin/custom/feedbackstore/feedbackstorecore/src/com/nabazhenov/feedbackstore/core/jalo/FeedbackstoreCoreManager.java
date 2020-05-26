/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.nabazhenov.feedbackstore.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.nabazhenov.feedbackstore.core.constants.FeedbackstoreCoreConstants;
import com.nabazhenov.feedbackstore.core.setup.CoreSystemSetup;


/**
 * Do not use, please use {@link CoreSystemSetup} instead.
 * 
 */
public class FeedbackstoreCoreManager extends GeneratedFeedbackstoreCoreManager
{
	public static final FeedbackstoreCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (FeedbackstoreCoreManager) em.getExtension(FeedbackstoreCoreConstants.EXTENSIONNAME);
	}
}
