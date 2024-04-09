from .views import CreatorViewSet, IssueViewSet, NoteViewSet, TagViewSet
from django.urls import path, include

urlpatterns = [
    path('creators', CreatorViewSet.as_view(), name="creators"),
    path('creators/<int:pk>', CreatorViewSet.as_view(), name="creators_get"),
    path('issues', IssueViewSet.as_view(), name="issues"),
    path('issues/<int:pk>', IssueViewSet.as_view(), name="issues_get"),
    path('notes', NoteViewSet.as_view(), name="notes"),
    path('notes/<int:pk>', NoteViewSet.as_view(), name="notes_get"),
    path('tags', TagViewSet.as_view(), name="tags"),
    path('tags/<int:pk>', TagViewSet.as_view(), name="tags_get"),
]