/**
 * Copyright (c) 2010 jolira. All rights reserved. This program and the accompanying materials are made available under
 * the terms of the GNU Public License 2.0 which is available at http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package com.google.code.joliratools.plugins.gae;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.capabilities.CapabilitiesService;
import com.google.appengine.api.capabilities.CapabilitiesServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provider;

/**
 * Register the most commmon interfaces provides by the GAE with Guice.
 * 
 * @author jfk
 * @date Sep 20, 2010 4:31:31 PM
 * @since 1.0
 */
public class GoogleAppEngineModule implements Module {
    /**
     * Register the interfaces.
     */
    @Override
    public void configure(final Binder binder) {
        binder.bind(UserService.class).toProvider(new Provider<UserService>() {
            @Override
            public UserService get() {
                return UserServiceFactory.getUserService();
            }
        });
        binder.bind(BlobstoreService.class).toProvider(new Provider<BlobstoreService>() {
            @Override
            public BlobstoreService get() {
                return BlobstoreServiceFactory.getBlobstoreService();
            }
        });
        binder.bind(CapabilitiesService.class).toProvider(new Provider<CapabilitiesService>() {
            @Override
            public CapabilitiesService get() {
                return CapabilitiesServiceFactory.getCapabilitiesService();
            }
        });
        binder.bind(DatastoreService.class).toProvider(new Provider<DatastoreService>() {
            @Override
            public DatastoreService get() {
                return DatastoreServiceFactory.getDatastoreService();
            }
        });
    }
}
