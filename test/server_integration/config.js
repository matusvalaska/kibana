/*
 * Licensed to Elasticsearch B.V. under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch B.V. licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import {
  createKibanaSupertestProvider,
  KibanaSupertestWithoutAuthProvider,
  ElasticsearchSupertestProvider,
} from './services';

export default async function ({ readConfigFile }) {
  const commonConfig = await readConfigFile(require.resolve('../common/config'));
  const functionalConfig = await readConfigFile(require.resolve('../functional/config'));

  return {
    services: {
      ...commonConfig.get('services'),
      supertest: createKibanaSupertestProvider(),
      supertestWithoutAuth: KibanaSupertestWithoutAuthProvider,
      esSupertest: ElasticsearchSupertestProvider,
    },
    servers: commonConfig.get('servers'),
    junit: {
      reportName: 'Integration Tests',
    },
    esTestCluster: commonConfig.get('esTestCluster'),
    kbnTestServer: {
      ...functionalConfig.get('kbnTestServer'),
      serverArgs: [
        ...functionalConfig.get('kbnTestServer.serverArgs'),
        '--elasticsearch.healthCheck.delay=3600000',
        '--server.xsrf.disableProtection=true',
      ],
    },
  };
}
