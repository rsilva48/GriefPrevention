package me.ryanhamshire.GriefPrevention;

import org.khelekore.prtree.MBRConverter;

class ClaimToMinRegion implements MBRConverter<Claim>
{
    @Override
    public int getDimensions()
    {
        return 2;
    }

    @Override
    public double getMin(int axis, Claim claim)
    {
        return axis == 0 ? claim.getLesserBoundaryCorner().getBlockX() : claim.getLesserBoundaryCorner().getBlockZ();
    }

    @Override
    public double getMax(int axis, Claim claim)
    {
        return axis == 0 ? claim.getGreaterBoundaryCorner().getBlockX() : claim.getGreaterBoundaryCorner().getBlockZ();
    }

    static int getBranchFactor(int claims)
    {
        return 30 + claims / 1000;
    }

}
