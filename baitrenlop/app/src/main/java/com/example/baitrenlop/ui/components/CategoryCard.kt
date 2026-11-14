package com.example.baitrenlop.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baitrenlop.ui.theme.TextPrimary
import com.example.baitrenlop.ui.theme.TextSecondary
import com.example.baitrenlop.ui.theme.UthBlue
import com.example.baitrenlop.ui.theme.UthBlueBg
import com.example.baitrenlop.ui.theme.UthOrange
import com.example.baitrenlop.ui.theme.UthOrangeBg

@Composable
fun CategoryRow(
    studyRemainingCount: Int,
    lifeRemainingCount: Int,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CategoryCard(
            title = "Học tập",
            remainingCount = studyRemainingCount,
            icon = Icons.Default.School,
            iconColor = UthBlue,
            bgColor = UthBlueBg,
            isSelected = selectedCategory == "Học tập",
            onClick = { onCategorySelect(if (selectedCategory == "Học tập") "Tất cả" else "Học tập") },
            modifier = Modifier.weight(1f)
        )

        CategoryCard(
            title = "Cuộc sống",
            remainingCount = lifeRemainingCount,
            icon = Icons.Default.Home,
            iconColor = UthOrange,
            bgColor = UthOrangeBg,
            isSelected = selectedCategory == "Cuộc sống",
            onClick = { onCategorySelect(if (selectedCategory == "Cuộc sống") "Tất cả" else "Cuộc sống") },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CategoryCard(
    title: String,
    remainingCount: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    bgColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) bgColor else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextPrimary
                )
                Text(
                    text = "$remainingCount việc còn lại",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
        }
    }
}
