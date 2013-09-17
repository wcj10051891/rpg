package com.commonrpg.modules.cultivationskill;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.commonrpg.cache.AbstractCacheObject;
import com.commonrpg.cache.CacheObject;
import com.commonrpg.persist.dao.CultivationSkillDao;
import com.commonrpg.service.Services;

public class CultivationSkill extends AbstractCacheObject<Integer> {

	private CultivationSkillDao dao = Services.daoFactory.get(CultivationSkillDao.class);
	public com.commonrpg.persist.entity.CultivationSkill entity;

	@Override
	public CacheObject<Integer> load(Integer id) {
		this.entity = dao.get(id);
		this.id = id;
		return this;
	}

	@Override
	public Map<Integer, CacheObject<Integer>> gets(List<Integer> ids) {
		Map<Integer, CacheObject<Integer>> result = new LinkedHashMap<Integer, CacheObject<Integer>>(ids.size());
		for (com.commonrpg.persist.entity.CultivationSkill e : dao.gets(ids))
			result.put(e.id, new CultivationSkill().load(e.id));
		return result;
	}

	public void save() {
		dao.update(entity);
	}

	public void delete() {
		dao.delete(entity.id);
	}
}
