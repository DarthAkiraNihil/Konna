# Contributing to Konna

## ⚠️ Warning! ⚠️

* All code that is about to be merged must be written according to the codestyle
(see [CODESTYLE.md](CODESTYLE.md) for more information). Same for commit messages.
* All features should fit project core principles and roadmap.
* In case if the feature is not in the roadmap, then it should be discussed.

## Community standards
* If you think that standard files of the repo (they are [readme](README.md),
[contributing guide](CONTRIBUTING.md), [codestyle guide](CODESTYLE.md)) need to be rewritten or clarified,
then the help will be highly appreciated.
* It is important to notice that such changes must be explained in a PR comments as the project owner
does not have enough experience in maintaining public projects

## Pull requests

* The rules of "Community standards" are not applied here
* All changes have to be merged to the dev branch first. So, if it is merged to another,
it won't be approved until their target branch is set to dev.
* After merging, the branch from that changes were taken should be deleted.
* Pull request title should match: ```[#<issue>] <keyword>: <PR summary>```. It is allowed to address multiple
issues, so then need to be separated with a comma.
* Keyword is taken from the Codestyle guide and should be started with a capital letter.
* Pull request title example: ```[#4] Feat: add somethhing feature```
* Pull request description should describe all the work you have done as it will be included in the changelog.
That means you need to include sec, feat, fix and ref commits to the description. However, 
other commit types are highly appreciated to be in the description.
Also, don't include issue number in the PR description because it is already in the title.
* If a PR fails checks, it won't be merged until all issues are fixed.
* A PR have to be reviewed and approved by the project owner himself
* If there is a comment in a review that asks about how it works, when answering make explanation simple yet full enough

## Feature requests

* You want to offer a feature that would be nice in Konna,
you need to contact the project owner first. Of course, you are not forced to do it,
but it may save a lot of time on the PR discussion
* The feature will be rejected if it doesn't meet the core principles (see above)

## Documentation

* All added code must be documented with Javadoc comments, but be rational
* Don't need to write docs for trivial methods, such as getters and setters, if their behaviour is common.
However, if a getter or setter does some additional stuff (i.g. reopening a resource), then it should be documented
* Always specify author and version (upcoming) for new classes
* Don't write docs for private members and methods
* Internal classes (e.g. package-private) are not required to be documented as well as tests

## Testing

* All written code should be covered at least in the way overall project coverage
is equal or greater than 90%.
* All test classes have to extend in any way [KStandardTestClass](core/src/main/java/io/github/darthakiranihil/konna/core/test/KStandardTestClass.java)
* Tests don't need to be documented