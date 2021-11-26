package com.example.androidproject_coupon.User;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.androidproject_coupon.AccountFragment;
import com.example.androidproject_coupon.CouponFragment;
import com.example.androidproject_coupon.OrderFragment;
import com.example.androidproject_coupon.ProductFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1 :
                return new CartFragment();
            case 2 :
                return new OrderFragment();
        }

        return new ShopFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
