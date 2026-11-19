import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import ComplianceFrameworks from '../components/ComplianceFrameworks.vue';
import ComplianceFrameworkDetail from '../components/ComplianceFrameworkDetail.vue';
import ComplianceControls from '../components/ComplianceControls.vue';
import ComplianceControlDetail from '../components/ComplianceControlDetail.vue';
import ComplianceAssessments from '../components/ComplianceAssessments.vue';
import ComplianceAssessmentDetail from '../components/ComplianceAssessmentDetail.vue';
import ComplianceFindings from '../components/ComplianceFindings.vue';
import ComplianceFindingDetail from '../components/ComplianceFindingDetail.vue';
import RemediationActions from '../components/RemediationActions.vue';
import RemediationActionDetail from '../components/RemediationActionDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/complianceFrameworks',
    name: 'ComplianceFrameworks',
    component: ComplianceFrameworks
  },
  {
    path: '/complianceFramework/:id',
    name: 'ComplianceFrameworkDetail',
    component: ComplianceFrameworkDetail,
    props: true
  },
  {
    path: '/complianceControls',
    name: 'ComplianceControls',
    component: ComplianceControls
  },
  {
    path: '/complianceControl/:id',
    name: 'ComplianceControlDetail',
    component: ComplianceControlDetail,
    props: true
  },
  {
    path: '/complianceAssessments',
    name: 'ComplianceAssessments',
    component: ComplianceAssessments
  },
  {
    path: '/complianceAssessment/:id',
    name: 'ComplianceAssessmentDetail',
    component: ComplianceAssessmentDetail,
    props: true
  },
  {
    path: '/complianceFindings',
    name: 'ComplianceFindings',
    component: ComplianceFindings
  },
  {
    path: '/complianceFinding/:id',
    name: 'ComplianceFindingDetail',
    component: ComplianceFindingDetail,
    props: true
  },
  {
    path: '/remediationActions',
    name: 'RemediationActions',
    component: RemediationActions
  },
  {
    path: '/remediationAction/:id',
    name: 'RemediationActionDetail',
    component: RemediationActionDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
