package net.goki.stats;

public class StatFocus extends Stat
{
	public StatFocus(int id, String key, int limit)
	{
		super(id, key, limit);
	}

	@Override
	public float getBonus(int level)
	{
		return getFinalBonus((float) Math.pow(level, 1.36D) * 0.03767F);
	}
}