--
-- Schema upgrade from 5.3.6 to 5.3.7;
--

UPDATE `cloud`.`hypervisor_capabilities` SET `vm_snapshot_enabled` = 1 WHERE `hypervisor_type` = 'KVM' AND `hypervisor_version` = 'default';

ALTER TABLE `cloud`.`domain` ADD `email` varchar(255);

DROP VIEW IF EXISTS `cloud`.`domain_view`;
CREATE VIEW `cloud`.`domain_view` AS
    select
        domain.id id,
        domain.parent parent,
        domain.name name,
        domain.uuid uuid,
        domain.owner owner,
        domain.path path,
        domain.level level,
        domain.child_count child_count,
        domain.next_child_seq next_child_seq,
        domain.removed removed,
        domain.state state,
        domain.network_domain network_domain,
        domain.type type,
        domain.email email,
        vmlimit.max vmLimit,
        vmcount.count vmTotal,
        iplimit.max ipLimit,
        ipcount.count ipTotal,
        volumelimit.max volumeLimit,
        volumecount.count volumeTotal,
        snapshotlimit.max snapshotLimit,
        snapshotcount.count snapshotTotal,
        templatelimit.max templateLimit,
        templatecount.count templateTotal,
        vpclimit.max vpcLimit,
        vpccount.count vpcTotal,
        projectlimit.max projectLimit,
        projectcount.count projectTotal,
        networklimit.max networkLimit,
        networkcount.count networkTotal,
        cpulimit.max cpuLimit,
        cpucount.count cpuTotal,
        memorylimit.max memoryLimit,
        memorycount.count memoryTotal,
        primary_storage_limit.max primaryStorageLimit,
        primary_storage_count.count primaryStorageTotal,
        secondary_storage_limit.max secondaryStorageLimit,
        secondary_storage_count.count secondaryStorageTotal
    from
        `cloud`.`domain`
            left join
        `cloud`.`resource_limit` vmlimit ON domain.id = vmlimit.domain_id
            and vmlimit.type = 'user_vm'
            left join
        `cloud`.`resource_count` vmcount ON domain.id = vmcount.domain_id
            and vmcount.type = 'user_vm'
            left join
        `cloud`.`resource_limit` iplimit ON domain.id = iplimit.domain_id
            and iplimit.type = 'public_ip'
            left join
        `cloud`.`resource_count` ipcount ON domain.id = ipcount.domain_id
            and ipcount.type = 'public_ip'
            left join
        `cloud`.`resource_limit` volumelimit ON domain.id = volumelimit.domain_id
            and volumelimit.type = 'volume'
            left join
        `cloud`.`resource_count` volumecount ON domain.id = volumecount.domain_id
            and volumecount.type = 'volume'
            left join
        `cloud`.`resource_limit` snapshotlimit ON domain.id = snapshotlimit.domain_id
            and snapshotlimit.type = 'snapshot'
            left join
        `cloud`.`resource_count` snapshotcount ON domain.id = snapshotcount.domain_id
            and snapshotcount.type = 'snapshot'
            left join
        `cloud`.`resource_limit` templatelimit ON domain.id = templatelimit.domain_id
            and templatelimit.type = 'template'
            left join
        `cloud`.`resource_count` templatecount ON domain.id = templatecount.domain_id
            and templatecount.type = 'template'
            left join
        `cloud`.`resource_limit` vpclimit ON domain.id = vpclimit.domain_id
            and vpclimit.type = 'vpc'
            left join
        `cloud`.`resource_count` vpccount ON domain.id = vpccount.domain_id
            and vpccount.type = 'vpc'
            left join
        `cloud`.`resource_limit` projectlimit ON domain.id = projectlimit.domain_id
            and projectlimit.type = 'project'
            left join
        `cloud`.`resource_count` projectcount ON domain.id = projectcount.domain_id
            and projectcount.type = 'project'
            left join
        `cloud`.`resource_limit` networklimit ON domain.id = networklimit.domain_id
            and networklimit.type = 'network'
            left join
        `cloud`.`resource_count` networkcount ON domain.id = networkcount.domain_id
            and networkcount.type = 'network'
            left join
        `cloud`.`resource_limit` cpulimit ON domain.id = cpulimit.domain_id
            and cpulimit.type = 'cpu'
            left join
        `cloud`.`resource_count` cpucount ON domain.id = cpucount.domain_id
            and cpucount.type = 'cpu'
            left join
        `cloud`.`resource_limit` memorylimit ON domain.id = memorylimit.domain_id
            and memorylimit.type = 'memory'
            left join
        `cloud`.`resource_count` memorycount ON domain.id = memorycount.domain_id
            and memorycount.type = 'memory'
            left join
        `cloud`.`resource_limit` primary_storage_limit ON domain.id = primary_storage_limit.domain_id
            and primary_storage_limit.type = 'primary_storage'
            left join
        `cloud`.`resource_count` primary_storage_count ON domain.id = primary_storage_count.domain_id
            and primary_storage_count.type = 'primary_storage'
            left join
        `cloud`.`resource_limit` secondary_storage_limit ON domain.id = secondary_storage_limit.domain_id
            and secondary_storage_limit.type = 'secondary_storage'
            left join
        `cloud`.`resource_count` secondary_storage_count ON domain.id = secondary_storage_count.domain_id
            and secondary_storage_count.type = 'secondary_storage';
