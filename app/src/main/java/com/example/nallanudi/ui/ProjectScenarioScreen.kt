package com.example.nallanudi.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nallanudi.R
import com.example.nallanudi.ui.theme.NallaNudiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectScenarioScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.project_scenario_title),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_arrow),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_find_in_page),
                            contentDescription = "Search in page"
                        )
                    }
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_to_drive),
                            contentDescription = "Add to drive"
                        )
                    }
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_more_vert),
                            contentDescription = "More options"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = Color(0xFFEADDFF)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Edit"
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.matrix),
                        color = Color(0xFF4A148C),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = stringResource(R.string.internship_program),
                        color = Color(0xFF4A148C),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.project_title_label),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Text(
                    text = stringResource(R.string.project_main_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                SectionHeader(text = stringResource(R.string.problem_statement_header))
                Text(
                    text = stringResource(R.string.problem_statement_text),
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp
                )
            }

            item {
                SectionHeader(text = stringResource(R.string.detailed_description_header))
                Text(
                    text = stringResource(R.string.detailed_description_text),
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp
                )
            }

            item {
                SectionHeader(text = stringResource(R.string.app_usage_header))
                BulletItem(text = stringResource(R.string.app_usage_term_search))
                BulletItem(text = stringResource(R.string.app_usage_subject_filters))
                BulletItem(text = stringResource(R.string.app_usage_voice_guide))
                BulletItem(text = stringResource(R.string.app_usage_my_list))
            }

            item {
                SectionHeader(text = stringResource(R.string.technical_implementation_header))
                BulletItem(text = stringResource(R.string.tech_impl_database))
                BulletItem(text = stringResource(R.string.tech_impl_tts))
                BulletItem(text = stringResource(R.string.tech_impl_ui))
            }

            item {
                SectionHeader(text = stringResource(R.string.impact_goals_header))
                BulletItem(text = stringResource(R.string.impact_equitable_education))
                BulletItem(text = stringResource(R.string.impact_skill_readiness))
                BulletItem(text = stringResource(R.string.impact_linguistic_pride))
            }

            item {
                SectionHeader(text = stringResource(R.string.success_criteria_header))
                BulletItem(text = stringResource(R.string.success_criteria_instant))
                BulletItem(text = stringResource(R.string.success_criteria_offline))
                BulletItem(text = stringResource(R.string.success_criteria_flashcard))
            }
            
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.matrix),
                        color = Color(0xFF4A148C),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = stringResource(R.string.internship_program),
                        color = Color(0xFF4A148C),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(top = 8.dp)
    )
}

@Composable
fun BulletItem(text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(vertical = 2.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = "• ", style = MaterialTheme.typography.bodyMedium)
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProjectScenarioScreenPreview() {
    NallaNudiTheme {
        ProjectScenarioScreen()
    }
}
