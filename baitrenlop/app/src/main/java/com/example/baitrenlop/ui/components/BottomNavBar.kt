package com.example.baitrenlop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baitrenlop.ui.theme.TextSecondary
import com.example.baitrenlop.ui.theme.UthBlue

enum class NavItem(val route: String, val title: String, val icon: ImageVector) {
    HOME("home", "Trang chủ", Icons.Default.Home),
    LIST("list", "Danh sách", Icons.Default.FormatListBulleted),
    NOTIFICATIONS("notifications", "Thông báo", Icons.Default.Notifications),
    PROFILE("profile", "Cài đặt", Icons.Default.Person)
}

@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home
            NavItemButton(
                item = NavItem.HOME,
                isSelected = currentRoute == NavItem.HOME.route,
                onClick = { onNavigate(NavItem.HOME.route) }
            )

            // List
            NavItemButton(
                item = NavItem.LIST,
                isSelected = currentRoute == NavItem.LIST.route,
                onClick = { onNavigate(NavItem.LIST.route) }
            )

            // Center FAB "+"
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(UthBlue)
                    .clickable { onAddClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Thêm công việc",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Notifications
            NavItemButton(
                item = NavItem.NOTIFICATIONS,
                isSelected = currentRoute == NavItem.NOTIFICATIONS.route,
                onClick = { onNavigate(NavItem.NOTIFICATIONS.route) }
            )

            // Profile / Settings
            NavItemButton(
                item = NavItem.PROFILE,
                isSelected = currentRoute == NavItem.PROFILE.route,
                onClick = { onNavigate(NavItem.PROFILE.route) }
            )
        }
    }
}

@Composable
private fun NavItemButton(
    item: NavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = if (isSelected) UthBlue else TextSecondary

    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 6.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = color,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = item.title,
            color = color,
            fontSize = 11.sp
        )
    }
}
